/*
package app.seven.chew.auth.web

import app.seven.chew.BaseIntegrationTest
import io.kotest.matchers.equals.shouldBeEqual
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.MvcResult
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext


class AuthControllerIntegrationTest : BaseIntegrationTest() {
    @Autowired
    val webApplicationContext: WebApplicationContext? = null

    var mockMvc: MockMvc? = null

    @BeforeEach
    @Throws(Exception::class)
    fun setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext!!).build()
    }

    @Nested
    inner class SignupTest {

        @Test
        fun `Test signup - when data is valid, it should create new account`() {
            val request = MockMvcRequestBuilders.get("/greet", "")
            val mvcResult: MvcResult = mockMvc!!.perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Hello World!!!"))
                .andReturn()

            "application/json;charset=UTF-8".shouldBeEqual(mvcResult.response.contentType!!)
        }
    }
    */
/*
        @Test
        fun login() {
        }

        @Test
        fun validate() {
        }

        @Test
        fun requestPasswordReset() {
        }

        @Test
        fun completePasswordReset() {
        }

        @Test
        fun refreshAccessToken() {
        }

        @Test
        fun changePassword() {
        }

        @Test
        fun getAuthBusiness() {
        }*//*

}*/
