package net.mindzone.robroo.ui.main.home.touchhelper

interface ItemTouchHelperAdapter {
    fun onItemMove(fromPosition: Int, toPosition: Int) : Boolean
    fun onItemDismiss(position:Int)

}