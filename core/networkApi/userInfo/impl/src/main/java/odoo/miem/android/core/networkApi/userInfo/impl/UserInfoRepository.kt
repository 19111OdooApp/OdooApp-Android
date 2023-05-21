package odoo.miem.android.core.networkApi.userInfo.impl

import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import odoo.miem.android.core.jsonRpcApiFabric.jsonRpcApi
import odoo.miem.android.core.networkApi.userInfo.api.IUserInfoRepository
import odoo.miem.android.core.networkApi.userInfo.api.source.UserInfoResponse
import odoo.miem.android.core.networkApi.userInfo.api.source.UserProfileResponse
import odoo.miem.android.core.networkApi.userInfo.impl.source.IUserInfoService
import timber.log.Timber
import javax.inject.Inject

/**
 * [UserInfoRepository] - implementation of [IUserInfoRepository]
 *
 * @author Egor Danilov
 */
class UserInfoRepository @Inject constructor() : IUserInfoRepository {

    private val userInfo by jsonRpcApi<IUserInfoService>()

    override fun getUserInfo(): Single<UserInfoResponse> {
        Timber.d("getUserInfo()")

        return Single.fromCallable {
            userInfo.getUserInfo()
        }.subscribeOn(Schedulers.io())
    }

    override fun getUserProfile(userId: Long): Single<UserProfileResponse> {
        Timber.d("getUserProfile(): userId - $userId")

        return Single.fromCallable {
            userInfo.getUserProfile(
                args = listOf(
                    listOf(userId),
                    userProfileFields
                )
            ).first()
        }.subscribeOn(Schedulers.io())
    }

    private companion object {
        val userProfileFields = listOf(
            "id",
            "name",
            "avatar_128",
            "work_phone",
            "mobile_phone",
            "email",
            "tz",
            "company_id",
            "address_id",
        )
    }
}
