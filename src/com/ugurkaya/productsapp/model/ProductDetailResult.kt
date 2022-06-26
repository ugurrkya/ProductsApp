import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.ugurkaya.productsapp.model.Result

data class ProductDetailResult(
    @SerializedName("mkName") @Expose var mkName: String?,
    @SerializedName("productName") @Expose var productName: String?,
    @SerializedName("badge") @Expose var badge: String?,
    @SerializedName("rating") @Expose var rating: Double?,
    @SerializedName("imageUrl") @Expose var imageUrl: String?,
    @SerializedName("storageOptions") @Expose var storageOptions: ArrayList<String> = arrayListOf(),
    @SerializedName("countOfPrices") @Expose var countOfPrices: Int?,
    @SerializedName("price") @Expose var price: Int?,
    @SerializedName("freeShipping") @Expose var freeShipping: Boolean?,
    @SerializedName("lastUpdate") @Expose var lastUpdate: String?
)