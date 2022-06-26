import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Products (

	@SerializedName("code")  @Expose val code : Int,
	@SerializedName("imageUrl")  @Expose val imageUrl : String,
	@SerializedName("name")  @Expose val name : String,
	@SerializedName("dropRatio")  @Expose val dropRatio : Double?,
	@SerializedName("price")  @Expose val price : Double,
	@SerializedName("countOfPrices")  @Expose val countOfPrices : Int?,
	@SerializedName("followCount")  @Expose val followCount : Int
)