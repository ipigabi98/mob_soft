package com.example.mobsoftapp.persistence

import com.example.mobsoftapp.model.Product
import com.example.mobsoftapp.utils.MockTestUtil.mockProductList
import kotlinx.coroutines.runBlocking
import org.hamcrest.MatcherAssert
import org.hamcrest.core.Is.`is`
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [21])
class ProductDaoTest : LocalDatabase() {

    private lateinit var productDao: ProductDao

    @Before
    fun init() {
        productDao = db.productDao()
    }

    @Test
    fun insertAndLoadPosterListTest() = runBlocking {
        val mockDataList = mockProductList()
        productDao.insertProductList(mockDataList)

        val loadFromDB = productDao.getProductList()
        MatcherAssert.assertThat(loadFromDB.toString(), `is`(mockDataList.toString()))

        val mockData = Product.mock()
        MatcherAssert.assertThat(loadFromDB[0].toString(), `is`(mockData.toString()))
    }

    @Test
    fun insertAndLoadProduct() = runBlocking {
        var mockData = Product.mock()

        productDao.insertProduct(mockData)
        var loadedProduct = productDao.getProduct(1L)

        MatcherAssert.assertThat(loadedProduct.toString(), `is`(mockData.toString()))
    }

    @Test
    fun updateProduct() = runBlocking {
        val mockDataList = mockProductList()
        productDao.insertProductList(mockDataList)

        val mockData = Product.mock()
        mockData.description = "updating product"

        productDao.updateProduct(mockData)

        val loadFromDB = productDao.getProductList()

        MatcherAssert.assertThat(loadFromDB[0].description, `is`("updating product"))
    }

    @Test
    fun deleteProduct() = runBlocking {
        val mockDataList = mockProductList()
        productDao.insertProductList(mockDataList)

        val mockData = Product.mock()
        mockData.id?.let { productDao.deleteProduct(it) }

        val loadFromDB = productDao.getProductList()

        MatcherAssert.assertThat(loadFromDB.size.toString(), `is` ("0"))
    }
}