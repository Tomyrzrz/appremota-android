package com.softim.bdremota_timo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.softim.bdremota_timo.databinding.ActivityVerBinding

class Ver : AppCompatActivity() {

    private lateinit var binding: ActivityVerBinding
    private lateinit var basedatos: FirebaseFirestore
    private var adaptador: AdaptadorProductoFirestore ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        basedatos = FirebaseFirestore.getInstance()

        val consulta = basedatos.collection("productos")

        val opciones = FirestoreRecyclerOptions.Builder<ProductModel>()
            .setQuery(consulta, ProductModel::class.java)
            .build()

        binding.rcvProductos.layoutManager = LinearLayoutManager(this)
        adaptador = AdaptadorProductoFirestore(opciones)
        binding.rcvProductos.adapter = adaptador
    }

    override fun onStart() {
        super.onStart()
        adaptador!!.startListening()
    }

    override fun onStop() {
        super.onStop()
        adaptador!!.stopListening()
    }

    private inner class AdaptadorProductoFirestore constructor(options: FirestoreRecyclerOptions<ProductModel>):
        FirestoreRecyclerAdapter<ProductModel, ProductViewHolder>(options){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_producto, parent, false)
            return ProductViewHolder(view)
        }

        override fun onBindViewHolder(holder: ProductViewHolder, position: Int, model: ProductModel) {
            holder.setProducto(model.producto)
            holder.setCantidad(model.cantidad)
            holder.setPrecio(model.precio)
        }

    }

    private inner class ProductViewHolder constructor(private val view: View): RecyclerView.ViewHolder(view) {
        fun setProducto(nombreProducto: String){
            val texto = view.findViewById<TextView>(R.id.txt_productoProd)
            texto.text = nombreProducto
        }
        fun setCantidad(cantidadProducto: Int){
            val texto = view.findViewById<TextView>(R.id.txt_cantidadProd)
            texto.text = cantidadProducto.toString()
        }
        fun setPrecio(precioProducto: Double){
            val texto = view.findViewById<TextView>(R.id.txt_precioProd)
            texto.text = precioProducto.toString()
        }
    }

}
