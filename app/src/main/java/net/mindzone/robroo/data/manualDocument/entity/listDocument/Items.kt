package net.mindzone.robroo.data.manualDocument.entity.listDocument

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable
//@Entity(tableName = "itempdf")
@Entity(tableName = "itempdf", indices = [Index(value = ["productmodelcode","docnumber","docid"], unique = true)])
data class Items (
        @PrimaryKey(autoGenerate = true)
        var id:Int=0,
        @SerializedName("docid")
        val docid: Int = 0,
        @SerializedName("docnumber")
        val docnumber: String = "",
        @SerializedName("issuedate")
        val issuedate: String = "" ,
        @SerializedName("productmodelcode")
        val productmodelcode: String = "",
        @SerializedName("productmodelprefix")
        val productmodelprefix: String = "",
        @SerializedName("productrgroupid")
        val productrgroupid: Int = 0,
        @SerializedName("productgrouptype")
        val productgrouptype: String = "",
        @SerializedName("productgroupnameth")
        val productgroupnameth: String = "",
        @SerializedName("productgroupnameen")
        val productgroupnameen: String = "",
        @SerializedName("productid")
        val productid: Int = 0,
        @SerializedName("productnameth")
        val productnameth: String = "",
        @SerializedName("productnameen")
        val productnameen: String= "",
        @SerializedName("doctypeid")
        val doctypeid: Int = 0,
        @SerializedName("doctypecode")
        val doctypecode: String = "",
        @SerializedName("doctypenameth")
        val doctypenameth: String = "",
        @SerializedName("doctypenameen")
        val doctypenameen: String = "",
        @SerializedName("doclangid")
        val doclangid: Int = 0,
        @SerializedName("doclangabbrv")
        val doclangabbrv: String = "",
        @SerializedName("doclangnameth")
        val doclangnameth: String = "",
        @SerializedName("doclangnameen")
        val doclangnameen: String= "",
        @SerializedName("viewonlineurl")
        val viewonlineurl: String? = "",
        @SerializedName("downloadurl")
        val downloadurl: String = "",
        var percent:Int=0,
        var status : Int = 1
) : Serializable