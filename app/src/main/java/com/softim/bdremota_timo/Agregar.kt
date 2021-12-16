package com.softim.bdremota_timo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import com.softim.bdremota_timo.databinding.ActivityAgregarBinding

class Agregar : AppCompatActivity() {

    private lateinit var binding: ActivityAgregarBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAgregarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnAgregar.setOnClickListener {
            if (binding.edtCantidad.text.toString() == "" ||
                    binding.edtProducto.text.toString() == "" ||
                    binding.edtPrecio.text.toString() == "")
                        Toast.makeText(this, "Llena todos los campos", Toast.LENGTH_SHORT).show()
            else {
                val producto = binding.edtProducto.text.toString()
                val cantidad = binding.edtCantidad.text.toString().toInt()
                val precio = binding.edtPrecio.text.toString().toDouble();
                insertaDatos(producto, cantidad, precio)
            }
        }

    }

    private fun insertaDatos(producto: String, cantidad: Int, precio: Double) {
        val base = FirebaseFirestore.getInstance()
        base.collection("productos")
            .add(ProductModel(producto,cantidad,precio))
            .addOnCompleteListener {
                Toast.makeText(this,"Insercion Correcta",Toast.LENGTH_SHORT).show()
                cleanFields()
            }
    }

    private fun cleanFields() {
        binding.edtCantidad.setText("")
        binding.edtPrecio.setText("")
        binding.edtProducto.setText("")
    }
}