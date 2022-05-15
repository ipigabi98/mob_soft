package com.example.mobsoftapp.network

import com.example.mobsoftapp.model.Product
import com.example.mobsoftapp.model.Rating
import com.skydoves.sandwich.ApiResponse
import com.skydoves.sandwich.SandwichInitializer
import com.skydoves.sandwich.coroutines.CoroutinesResponseCallAdapterFactory
import com.skydoves.sandwich.suspendOnSuccess
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@RunWith(JUnit4::class)
class StoreServiceTest {
    private lateinit var service: StoreService
    private lateinit var server: MockWebServer

    @Before
    fun setUp() {
        server = MockWebServer()
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
        runBlocking {
            enqueueMockResponse("getProductList.json")

            val apiResponse = ApiResponse.of(SandwichInitializer.successCodeRange) {
                runBlocking {
                    Response.success(service.getProductList())
                }
            }

            if (apiResponse is ApiResponse.Success) {
                apiResponse.data.suspendOnSuccess {
                    assertEquals(1, data.size)
                    assertEquals(1L, data[0].id)
                    assertEquals("getProductList_test_title", data[0].title)
                    assertEquals(10.10, data[0].price)
                    assertEquals("getProductList_test_description", data[0].description)
                    assertEquals("getProductList_test_category", data[0].category)
                    assertEquals("getProductList_test_image", data[0].image)
                    assertEquals(10.10, data[0].rating.rate)
                    assertEquals(10, data[0].rating.count)
                }
            }
        }
    }

    @Test
    fun test_getProductById() {
        runBlocking {
            enqueueMockResponse("getProductList.json")

            val apiResponse = ApiResponse.of(SandwichInitializer.successCodeRange) {
                runBlocking {
                    Response.success(service.getProductById(1))
                }
            }

            if (apiResponse is ApiResponse.Success) {
                apiResponse.data.suspendOnSuccess {
                    assertEquals(1L, data.id)
                    assertEquals("getProductById_test_title", data.title)
                    assertEquals(10.10, data.price)
                    assertEquals("getProductById_test_description", data.description)
                    assertEquals("getProductById_test_category", data.category)
                    assertEquals("getProductById_test_image", data.image)
                    assertEquals(10.10, data.rating.rate)
                    assertEquals(10, data.rating.count)
                }
            }
        }
    }

    @Test
    fun test_createProduct() {
        runBlocking {
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

            val apiResponse = ApiResponse.of(SandwichInitializer.successCodeRange) {
                runBlocking {
                    Response.success(service.createProduct(product))
                }
            }

            if (apiResponse is ApiResponse.Success) {
                apiResponse.data.suspendOnSuccess {
                    assertEquals(21L, data.id)
                    assertEquals("createProduct_test_title", data.title)
                    assertEquals(10.10, data.price)
                    assertEquals("createProduct_test_description", data.description)
                    assertEquals("createProduct_test_category", data.category)
                    assertEquals("createProduct_test_image", data.image)
                    assertEquals(10.10, data.rating.rate)
                    assertEquals(10, data.rating.count)
                }
            }
        }
    }

    @Test
    fun test_updateProduct() {
        runBlocking {
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

            val apiResponse = ApiResponse.of(SandwichInitializer.successCodeRange) {
                runBlocking {
                    Response.success(service.updateProduct(1, product))
                }
            }

            if (apiResponse is ApiResponse.Success) {
                apiResponse.data.suspendOnSuccess {
                    assertEquals(1L, data.id)
                    assertEquals("updateProduct_test_title", data.title)
                    assertEquals(10.10, data.price)
                    assertEquals("updateProduct_test_description", data.description)
                    assertEquals("updateProduct_test_category", data.category)
                    assertEquals("updateProduct_test_image", data.image)
                    assertEquals(10.10, data.rating.rate)
                    assertEquals(10, data.rating.count)
                }
            }
        }
    }

    @Test
    fun test_deleteProductById() {
        runBlocking {
            enqueueMockResponse("deleteProductById.json")

            val apiResponse = ApiResponse.of(SandwichInitializer.successCodeRange) {
                runBlocking {
                    Response.success(service.deleteProductById(1))
                }
            }

            if (apiResponse is ApiResponse.Success) {
                apiResponse.data.suspendOnSuccess {
                    assertEquals(1L, data.id)
                    assertEquals("deleteProductById_test_title", data.title)
                    assertEquals(10.10, data.price)
                    assertEquals("deleteProductById_test_description", data.description)
                    assertEquals("deleteProductById_test_category", data.category)
                    assertEquals("deleteProductById_test_image", data.image)
                    assertEquals(10.10, data.rating.rate)
                    assertEquals(10, data.rating.count)
                }
            }
        }
    }

    @After
    fun tearDown() {
        server.shutdown()
    }
}