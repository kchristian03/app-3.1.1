package Interface

interface cardListener {
    fun onCardClick(position: Int)

    fun onCardClick1(isEdit: Boolean, position: Int)
}