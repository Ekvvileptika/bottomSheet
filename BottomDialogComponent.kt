package az.myaccess.ui.components.bottomdialog

import az.myaccess.R
import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.core.view.updatePadding
import com.google.android.material.bottomsheet.BottomSheetDialog


class BottomDialogComponent(
    private val activity: Activity?,
    private val elements: ArrayList<BottomDialogModel>
) {
    private var selectedValue = ""
    private var bottomSheet: BottomSheetDialog? = null

    fun show(){
        activity?.let {
            bottomSheet = BottomSheetDialog(activity, R.style.AppBottomSheetDialogTheme)
            val view: View = activity.layoutInflater.inflate(
                R.layout.dialog_settings_confirmation, null
            )

            val rootElement = view.findViewById<LinearLayout>(R.id.parentBody)

            /***Fill body*/
            elements.forEach { element ->
                when(element.type){
                    //title
                    BottomDialogComponentName.TITLE -> {
                        val textView = TextView(activity)
                        textView.setTextAppearance(R.style.BottomSheetDialogTitleStyle)
                        textView.text = element.singleValue

                        rootElement.addView(textView)
                        rootElement.addView(addSpace(50))
                    }

                    //description
                    BottomDialogComponentName.DESCRIPTION -> {
                        val textView = TextView(activity)
                        textView.text = element.singleValue

                        rootElement.addView(textView)
                        rootElement.addView(addSpace(50))
                    }

                    //radio
                    BottomDialogComponentName.RADIO -> {
                        val radioGroup = RadioGroup(activity)
                        element.arrayValues?.forEachIndexed { index, radioElement ->
                            val radioButtonView: RadioButton = LayoutInflater.from(activity).inflate(R.layout.component_radio_element, null, false) as RadioButton
                            val params = RadioGroup.LayoutParams(
                                ViewGroup.LayoutParams.WRAP_CONTENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT
                            )
                            params.setMargins(15, 15, 15, 15)
                            radioButtonView.text = radioElement.title
                            radioButtonView.setTextAppearance(R.style.BottomSheetDialogRadioStyle)
                            radioButtonView.buttonDrawable = ContextCompat.getDrawable(activity, R.drawable.checkbox_selector)
                            radioButtonView.setOnClickListener {
                                selectedValue = index.toString()
                            }

                            radioGroup.addView(radioButtonView)
                        }

                        rootElement.addView(radioGroup)
                        rootElement.addView(addSpace(50))
                    }

                    //active button
                    BottomDialogComponentName.ACTIVEBUTTON -> {
                        val buttonView: Button = LayoutInflater.from(activity).inflate(R.layout.component_activebutton_element, null, false) as Button
                        buttonView.text = element.singleValue
                        buttonView.setOnClickListener {
                            element.actionListener?.invoke(selectedValue)
                        }

                        rootElement.addView(buttonView)
                    }

                    //text button
                    BottomDialogComponentName.TEXTBUTTON -> {
                        val buttonView: Button = LayoutInflater.from(activity).inflate(R.layout.component_inactivebutton_element, null, false) as Button
                        buttonView.text = element.singleValue
                        buttonView.setOnClickListener {
                            element.actionListener?.invoke(selectedValue)
                        }

                        rootElement.addView(buttonView)
                    }
                }
            }

            /***Fill body*/
            bottomSheet?.setContentView(view)
            bottomSheet?.show()
        }
    }

    //dismiss
    fun dismissWindow(){
        bottomSheet?.dismiss()
    }

    //add free space between elements
    private fun addSpace(height: Int): View{
        val view = View(activity)
        val params = RadioGroup.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        params.height = height
        view.layoutParams = params

        return view
    }

    class Builder{
        private var componentValue: ArrayList<BottomDialogModel> = arrayListOf()
        private var activity: Activity? = null

        fun setComponent(element: ArrayList<BottomDialogModel>) = apply{
            this.componentValue.addAll(element)
        }

        fun setActivity(activity: Activity?) = apply {
            this.activity = activity
        }

        fun build() = BottomDialogComponent(activity, componentValue)
    }
}