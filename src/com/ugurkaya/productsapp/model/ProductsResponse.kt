import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.ugurkaya.productsapp.model.Result

data class ProductsResponse (

	@SerializedName("result") @Expose val result : Result
)