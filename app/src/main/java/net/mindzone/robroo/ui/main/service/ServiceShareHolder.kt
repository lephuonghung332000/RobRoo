package net.mindzone.robroo.ui.main.service

import androidx.recyclerview.widget.RecyclerView
import net.mindzone.robroo.databinding.ItemServiceBinding
class ServiceShareHolder(val binding: ItemServiceBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(
        serviceViewModel: ServiceViewModel
        //contact: Contact?
        //share: Share?
    ) {
        binding.apply {
            this.serviceViewModel = serviceViewModel
        }
    }
}