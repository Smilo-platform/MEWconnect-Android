package com.myetherwallet.mewconnect.content.api.mew

import com.myetherwallet.mewconnect.feature.main.data.JsonRpcRequest
import com.myetherwallet.mewconnect.feature.main.data.JsonRpcResponse
import org.web3j.protocol.core.methods.request.Transaction
import retrofit2.Call
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by BArtWell on 16.07.2018.
 */

@Singleton
class MewApiService
@Inject constructor(client: MewClient) : MewApi {

    private val mewApi by lazy { client.retrofit.create(MewApi::class.java) }

    private val mewApiTest by lazy { client.retrofitTest.create(MewApi::class.java) }

    override fun getAllBalances(apiMethod: String, jsonRpc: JsonRpcRequest<Transaction>): Call<JsonRpcResponse> {
        return when (apiMethod == "xsm") {
            true -> mewApi.getAllBalances(apiMethod, jsonRpc)
            false -> mewApiTest.getAllBalances(apiMethod, jsonRpc)
        }
    }

    override fun getWalletBalance(apiMethod: String, jsonRpc: JsonRpcRequest<String>): Call<JsonRpcResponse> {
        return when (apiMethod == "xsm") {
            true -> mewApi.getWalletBalance(apiMethod, jsonRpc)
            false -> mewApiTest.getWalletBalance(apiMethod, jsonRpc)
        }
    }
}
