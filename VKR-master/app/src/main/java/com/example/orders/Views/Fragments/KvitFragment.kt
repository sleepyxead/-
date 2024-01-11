package com.example.orders.Views.Fragments

import android.Manifest
import android.content.Intent
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.pdf.PdfDocument
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import com.example.orders.Utilities.PreferenceManager
import com.example.orders.databinding.FragmentKvitBinding
import java.io.File
import java.io.FileOutputStream
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.Date


class KvitFragment : Fragment() {

    private lateinit var binding:FragmentKvitBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentKvitBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val manager = PreferenceManager(requireContext())
        binding.button.setOnClickListener {
            val kvit = PdfDocument()
            val myPaint = Paint()
            myPaint.textSize=25f
            val titlePaint = Paint()
            titlePaint.textAlign = Paint.Align.CENTER
            titlePaint.typeface = Typeface.create(Typeface.DEFAULT,Typeface.BOLD)
            titlePaint.textSize = 50f
            titlePaint.color = Color.parseColor("#05A20B")
            val pageInfo = PdfDocument.PageInfo.Builder(1200,2010,1).create()
            val page = kvit.startPage(pageInfo)
            val canvas = page.canvas
            //Заголовок
            canvas.drawText("ООО Сервис",600f,300f,titlePaint)
            //Номер
            canvas.drawText("Звонить по номеру 8 953-245-23-21",770f,40f,myPaint)
            //Квитанция
            //titlePaint.typeface = Typeface.create(Typeface.DEFAULT,Typeface.NORMAL)
            titlePaint.textSize=35f
            titlePaint.color = Color.BLACK
            canvas.drawText("Квитанция",600f,500f,titlePaint)
            //Имя мастера
            myPaint.textAlign = Paint.Align.LEFT
            canvas.drawText("Имя мастера: " + manager.getString("Name"),20f,590f,myPaint)
            //Дата
            myPaint.textAlign = Paint.Align.RIGHT
            val sdf = SimpleDateFormat("DD/MM/YYYY hh:mm:ss")
            val currentDate = sdf.format(Date())
            canvas.drawText("Время: " + currentDate,1175f,590f,myPaint)
            myPaint.textSize=20f
            //Таблица
            myPaint.style = Paint.Style.STROKE
            myPaint.strokeWidth=2f
            canvas.drawRect(20f,780f,1200f-20f,860f,myPaint)
            myPaint.textAlign = Paint.Align.LEFT
            myPaint.style = Paint.Style.FILL
            canvas.drawText("Номер",40f,830f,myPaint)
            canvas.drawText("Вид работы",200f,830f,myPaint)
            canvas.drawText("Стоимость",900f,830f,myPaint)
            canvas.drawLine(180f,790f,180f,840f,myPaint)
            canvas.drawLine(870f,790f,870f,840f,myPaint)

            canvas.drawText("1.",40f,950f,myPaint)
            canvas.drawText(binding.work1.text.toString(),200f,950f,myPaint)
            canvas.drawText(binding.cost1.text.toString() +" руб.",900f,950f,myPaint)
            canvas.drawText("2.",40f,1050f,myPaint)
            canvas.drawText(binding.work2.text.toString(),200f,1050f,myPaint)
            canvas.drawText(binding.cost2.text.toString() +" руб.",900f,1050f,myPaint)
            canvas.drawText("3.",40f,1150f,myPaint)
            canvas.drawText(binding.work3.text.toString(),200f,1150f,myPaint)
            canvas.drawText(binding.cost3.text.toString() +" руб.",900f,1150f,myPaint)

            var sum = binding.cost1.text.toString().toInt()+binding.cost2.text.toString().toInt()+binding.cost3.text.toString().toInt()
            //Общая сумма
            canvas.drawLine(680f,1300f,1180f,1300f,myPaint)
            canvas.drawText("Полная сумма",700f,1350f,myPaint)
            canvas.drawText(":",900f,1350f,myPaint)
            myPaint.textAlign = Paint.Align.RIGHT
            canvas.drawText(sum.toString() + " руб.",1160f,1350f,myPaint)

            val proc =  binding.proc.text.toString().toInt()
            myPaint.textAlign = Paint.Align.LEFT
            canvas.drawText("Скидка ("+ proc.toString()+"%)",700f,1400f,myPaint)
            canvas.drawText(":",900f,1400f,myPaint)
            myPaint.textAlign = Paint.Align.RIGHT
            canvas.drawText((sum*proc/100).toString()+" руб.",1160f,1400f,myPaint)

            myPaint.color = Color.parseColor("#05A20B")
            canvas.drawRect(680f,1450f,1180f,1550f,myPaint)

            myPaint.color = Color.WHITE
            myPaint.textSize = 40f
            myPaint.textAlign = Paint.Align.LEFT
            canvas.drawText("Итого",700f,1515f,myPaint)
            myPaint.textAlign = Paint.Align.RIGHT
            canvas.drawText((sum-(sum*proc/100)).toString()+" руб.",1160f,1515f,myPaint)
            kvit.finishPage(page)

            ActivityCompat.requestPermissions(
                requireActivity(), arrayOf<String>(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.MANAGE_EXTERNAL_STORAGE
                ), 1
            )

            try {
                val mediaStorageDirectory = File(
                    requireContext().getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS),
                    "PDFS"
                )
                if (!mediaStorageDirectory.exists()){
                    mediaStorageDirectory.mkdirs()
                }
                val fileName = "Kvit-${System.currentTimeMillis()}.pdf"
                val file = File(mediaStorageDirectory,fileName)
                kvit.writeTo(FileOutputStream(file))

                val path= FileProvider.getUriForFile(requireContext(),"${requireContext().packageName}.provider",file)
                with(Intent(Intent.ACTION_SEND)){
                    putExtra(Intent.EXTRA_STREAM, path)
                    addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                    type = "application/pd"
                    startActivity(this)}

            }catch (exception: Exception){
                null
            }



        }

    }
}