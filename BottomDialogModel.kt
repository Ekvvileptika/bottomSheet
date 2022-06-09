package az.myaccess.ui.components.bottomdialog

import com.google.android.gms.common.annotation.KeepName
import com.google.gson.annotations.SerializedName

@KeepName
data class BottomDialogModel (
    @SerializedName("type")
    val type: BottomDialogComponentName,
    @SerializedName("arrayValues")
    val arrayValues: ArrayList<BottomDialogCheckBox>? = arrayListOf(),
    @SerializedName("singleValue")
    val singleValue: String? = null,
    @SerializedName("actionListener")
    val actionListener: ((String?) -> Unit)? = null
)

@KeepName
data class BottomDialogCheckBox(
    @SerializedName("title")
    val title: String,
    @SerializedName("isSelected")
    val isSelected: Boolean = false
)

enum class BottomDialogComponentName{
    RADIO,
    TITLE,
    DESCRIPTION,
    ACTIVEBUTTON,
    TEXTBUTTON,
    CHECKBOX
}