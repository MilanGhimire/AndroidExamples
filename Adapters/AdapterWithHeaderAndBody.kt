
class OrderAdapter(
    private val context: Context,
    private val orderList : List<RecyclerViewContainer>,
    listener: OrderClickListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>()
{
    private val TYPE_HEADER : Int = 0
    private val TYPE_LIST : Int = 1

    private val orderClickListener: OrderClickListener = listener

    interface OrderClickListener {
        fun onIncreaseClicked(orderModel: OrderModel)
        fun onDecreaseClicked(orderModel: OrderModel)
    }

    override fun getItemViewType(position: Int): Int {
        if (orderList[position].isHeader)
            return 0
        else
            return 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if(viewType == TYPE_HEADER)
        {
            val header = LayoutInflater.from(parent.context).inflate(R.layout.tpl_order_header,parent,false)
            return ViewHolderHeader(header)
        }

        val header = LayoutInflater.from(parent.context).inflate(R.layout.tpl_order,parent,false)
        return ViewHolder(header)
    }

    override fun getItemCount(): Int {
        return orderList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val recyclerViewContainer : RecyclerViewContainer = orderList[position]

        if(holder is ViewHolderHeader)
        {
            holder.tvOrder.text = recyclerViewContainer.category?.categoryName
        }

        if(holder is ViewHolder)
        {
            holder.bind(recyclerViewContainer.orderModel ?: OrderModel())
        }
    }

    inner class ViewHolderHeader(itemView : View) : RecyclerView.ViewHolder(itemView)
    {
        val tvOrder = itemView.findViewById(R.id.headerText) as TextView
    }

    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView)
    {
        val ivFoodImage = itemView.findViewById(R.id.ivFoodImage) as ImageView
        val tvFoodName = itemView.findViewById(R.id.tvFoodName) as TextView
        val tvFoodPrice = itemView.findViewById(R.id.tvFoodPrice) as TextView
        val ivIncrease = itemView.findViewById(R.id.ivIncrease) as ImageView
        val tvQuantity = itemView.findViewById(R.id.tvQuantity) as TextView
        val ivDecrease = itemView.findViewById(R.id.ivDecrease) as ImageView

        fun bind(orderModel: OrderModel) {
//            if (!orderModel.image.isNullOrBlank())
                // ToDo load food image through Glide

            tvFoodName.text = orderModel.name
            tvFoodPrice.text = "Rs ${orderModel.price}"
            ivIncrease.setOnClickListener {
                orderClickListener.onIncreaseClicked(orderModel)
            }
            tvQuantity.text = orderModel.quantity.toString()
            ivDecrease.setOnClickListener {
                orderClickListener.onDecreaseClicked(orderModel)
            }
            Glide.with(context)
                .load("${MLProApplication.imageBaseURL}${orderModel.image}")
                .error(R.drawable.ic_image).into(ivFoodImage)
        }
    }
}