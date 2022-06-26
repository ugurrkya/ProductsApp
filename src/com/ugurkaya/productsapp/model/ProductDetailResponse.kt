import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.ugurkaya.productsapp.model.Result

data class ProductDetailResponse (

	@SerializedName("result") @Expose val result : ProductDetailResult
)