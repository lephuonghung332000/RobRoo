package net.mindzone.robroo.utils.extensions

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import net.mindzone.robroo.R
import net.mindzone.robroo.data.manualDocument.entity.listLanguage.ResponseData
class ArrayAdapterLanguageCustom(
    context: Context,
    private val textViewResourceId: Int,
    private var items: List<ResponseData>
) : ArrayAdapter<ResponseData>(context, textViewResourceId, items) {

    private var inflater: LayoutInflater = context.getSystemService(
        Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getCount(): Int = items.size + 1

    override fun getItem(position: Int): ResponseData =
        if (position == 0) ResponseData(0, "",context.getString(
            R.string.languageSpinner)) else items[position - 1]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View =
        if (position == 0) {
            getFirstTextView(convertView)
        } else {
            getTextView(convertView, parent, position - 1)
        }
    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View =
        getView(position, convertView, parent)

    private fun getFirstTextView(convertView: View?): View {
        var textView: TextView? = convertView as TextView?
        val holder: FirstViewHolder
        if (textView?.tag !is FirstViewHolder) {
            textView = TextView(context) // inflater.inflate(R.layout.your_text, parent, false) as TextView
            textView.height = 0 // Hide first item.
            holder = FirstViewHolder()
            textView.text=context.getString(R.string.languageSpinner)
            holder.textView = textView
            textView.tag = holder
        }
        return textView
    }

    private fun getTextView(
        convertView: View?,
        parent: ViewGroup,
        position: Int
    ): TextView {
        var textView: TextView? = convertView as TextView?
        val holder: ViewHolder
        if (textView?.tag is ViewHolder) {
            holder = textView.tag as ViewHolder
        } else {
            textView = inflater.inflate(textViewResourceId, parent, false) as TextView
            holder = ViewHolder()
            holder.textView = textView
            textView.tag = holder
        }
        holder.textView.text = items[position].toString()

        return textView
    }

    private class FirstViewHolder {
        lateinit var textView: TextView
    }

    private class ViewHolder {
        lateinit var textView: TextView
    }
}
