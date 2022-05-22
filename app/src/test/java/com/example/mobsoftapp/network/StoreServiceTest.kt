package com.example.mobsoftapp.network

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.mobsoftapp.model.Product
import com.example.mobsoftapp.model.Rating
import com.skydoves.sandwich.ApiResponse
import com.skydoves.sandwich.SandwichInitializer
import com.skydoves.sandwich.coroutines.CoroutinesResponseCallAdapterFactory
import com.skydoves.sandwich.suspendOnSuccess
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@RunWith(JUnit4::class)
class StoreServiceTest {

    @Rule
    @JvmField
    val instantExecutorRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var service: StoreService
    private lateinit var server: MockWebServer

    @Before
    fun setUp() {
        server = MockWebServer()
        server.start()
        service = Retrofit.Builder()
            .baseUrl(server.url(""))
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutinesResponseCallAdapterFactory.create())
            .build()
            .create(StoreService::class.java)
    }

    private fun enqueueMockResponse(fileName: String) {
        javaClass.classLoader?.let {
            val inputStream = it.getResourceAsStream(fileName)
            val source = inputStream.source().buffer()
            val mockResponse = MockResponse()
            mockResponse.setBody(source.readString(Charsets.UTF_8))
            server.enqueue(mockResponse)
        }
    }

    @Test
    fun test_getProductList() {
        enqueueMockResponse("getProductList.json")

        var response: List<Product>? = null
        runBlocking {
            withContext(Dispatchers.IO) {
                service.getProductList()
                    .suspendOnSuccess {
                        response = data
                    }
            }
        }

        assertEquals(1, response!!.size)
        assertEquals(1L, response!![0].id)
        assertEquals("getProductList_test_title", response!![0].title)
        assertEquals(10.10, response!![0].price)
        assertEquals("getProductList_test_description", response!![0].description)
        assertEquals("getProductList_test_category", response!![0].category)
        assertEquals("getProductList_test_image", response!![0].image)
        assertEquals(10.10, response!![0].rating.rate)
        assertEquals(10, response!![0].rating.count)
    }

    @Test
    fun test_getProductById() {
        enqueueMockResponse("getProductById.json")

        var response: Product? = null
        runBlocking {
            withContext(Dispatchers.IO) {
                service.getProductById(1)
                    .suspendOnSuccess {
                        response = data
                    }
            }
        }

        assertEquals(1L, response!!.id)
        assertEquals("getProductById_test_title", response!!.title)
        assertEquals(10.10, response!!.price)
        assertEquals("getProductById_test_description", response!!.description)
        assertEquals("getProductById_test_category", response!!.category)
        assertEquals("getProductById_test_image", response!!.image)
        assertEquals(10.10, response!!.rating.rate)
        assertEquals(10, response!!.rating.count)
    }

    @Test
    fun test_createProduct() {
        enqueueMockResponse("createProduct.json")

        var product = Product(
            id = null,
            title = "createProduct_test_title",
            price = 10.10,
            description = "createProduct_test_description",
            category = "createProduct_test_category",
            image = "createProduct_test_image",
            rating = Rating(
                rate = 10.10,
                count = 10
            )
        )

        var response: Product? = null
        runBlocking {
            withContext(Dispatchers.IO) {
                service.createProduct(product)
                    .suspendOnSuccess {
                        response = data
                    }
            }
        }

        assertEquals(21L, response!!.id)
        assertEquals("createProduct_test_title", response!!.title)
        assertEquals(10.10, response!!.price)
        assertEquals("createProduct_test_description", response!!.description)
        assertEquals("createProduct_test_category", response!!.category)
        assertEquals("createProduct_test_image", response!!.image)
        assertEquals(10.10, response!!.rating.rate)
        assertEquals(10, response!!.rating.count)
    }

    @Test
    fun test_updateProduct() {
        enqueueMockResponse("updateProduct.json")

        var product = Product(
            id = 1,
            title = "updateProduct_test_title",
            price = 10.10,
            description = "updateProduct_test_description",
            category = "updateProduct_test_category",
            image = "updateProduct_test_image",
            rating = Rating(
                rate = 10.10,
                count = 10
            )
        )

        var response: Product? = null
        runBlocking {
            withContext(Dispatchers.IO) {
                service.updateProduct(1, product)
                    .suspendOnSuccess {
                        response = data
                    }
            }
        }

        assertEquals(1L, response!!.id)
        assertEquals("updateProduct_test_title", response!!.title)
        assertEquals(10.10, response!!.price)
        assertEquals("updateProduct_test_description", response!!.description)
        assertEquals("updateProduct_test_category", response!!.category)
        assertEquals("updateProduct_test_image", response!!.image)
        assertEquals(10.10, response!!.rating.rate)
        assertEquals(10, response!!.rating.count)
    }

    @Test
    fun test_deleteProductById() {
        enqueueMockResponse("deleteProductById.json")

        var response: Product? = null
        runBlocking {
            withContext(Dispatchers.IO) {
                service.deleteProductById(1)
                    .suspendOnSuccess {
                        response = data
                    }
            }
        }

        assertEquals(1L, response!!.id)
        assertEquals("deleteProductById_test_title", response!!.title)
        assertEquals(10.10, response!!.price)
        assertEquals("deleteProductById_test_description", response!!.description)
        assertEquals("deleteProductById_test_category", response!!.category)
        assertEquals("deleteProductById_test_image", response!!.image)
        assertEquals(10.10, response!!.rating.rate)
        assertEquals(10, response!!.rating.count)
    }

    @After
    fun tearDown() {
        server.shutdown()
    }
}