import com.google.gson.annotations.SerializedName

data class HorizontalProducts (

	@SerializedName("code") val code : Int,
	@SerializedName("imageUrl") val imageUrl : String,
	@SerializedName("name") val name : String,
	@SerializedName("dropRatio") val dropRatio : Double?,
	@SerializedName("price") val price : Double,
	@SerializedName("countOfPrices") val countOfPrices : Int?,
	@SerializedName("followCount") val followCount : Int
)