package mj.project.shopping.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import mj.project.shopping.data.entitiy.product.ProductEntity
import mj.project.shopping.databinding.ViewholderProductItemBinding
import mj.project.shopping.extensions.loadCenterCrop

class ProductListAdapter : RecyclerView.Adapter<ProductListAdapter.ProductItemViewHolder>() {

    private var productList: List<ProductEntity> = listOf()

    private lateinit var productItemClickListener: (ProductEntity) -> Unit

    inner class ProductItemViewHolder(
        private val binding: ViewholderProductItemBinding,
        val productItemClickListener: (ProductEntity) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bindData(data: ProductEntity) = with(binding) {
            productNameTextView.text = data.productName
            productImageView.loadCenterCrop(data.productImage, 8f)
            productPriceTextView.text = "${data.productPrice}원"
        }

        fun bindViews(data: ProductEntity) {
            binding.root.setOnClickListener {
                productItemClickListener(data)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductItemViewHolder {
        val view = ViewholderProductItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductItemViewHolder(view, productItemClickListener)
    }

    override fun onBindViewHolder(holder: ProductItemViewHolder, position: Int) {
        holder.bindData(productList[position])
        holder.bindViews(productList[position])
    }

    override fun getItemCount(): Int = productList.size

    // 프로필화면에서 내가 주문한 제품이 없을경우를 대비하여 {} 처리했다.
    fun setProductList(productList: List<ProductEntity>, productItemClickListener: (ProductEntity) -> Unit = { }) {
        this.productList = productList
        this.productItemClickListener = productItemClickListener
        notifyDataSetChanged()
    }
}