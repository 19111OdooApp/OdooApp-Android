package odoo.miem.android.di

import android.content.Context
import dagger.Component
import odoo.miem.android.common.network.authorization.impl.di.AuthorizationInteractorApiProvider
import odoo.miem.android.common.network.employees.impl.di.EmployeesInteractorApiProvider
import odoo.miem.android.common.network.recruitment.impl.di.RecruitmentInteractorApiProvider
import odoo.miem.android.common.network.selectingModules.impl.di.SelectingModulesInteractorApiProvider
import odoo.miem.android.core.dataStore.impl.di.DataStoreApiProvider
import odoo.miem.android.core.di.impl.ApiRegistry
import odoo.miem.android.core.di.impl.ApiResolver
import odoo.miem.android.core.firebaseDatabase.impl.di.FirebaseDatabaseApiProvider
import odoo.miem.android.core.jsonrpc.converter.impl.di.MoshiSerializerApiProvider
import odoo.miem.android.core.networkApi.authorization.impl.di.AuthorizationRepositoryApiProvider
import odoo.miem.android.core.networkApi.crm.impl.di.CrmRepositoryApiProvider
import odoo.miem.android.core.networkApi.employees.api.di.EmployeesRepositoryApiProvider
import odoo.miem.android.core.networkApi.firebaseRemoteConfig.impl.di.FirebaseRemoteConfigApiProvider
import odoo.miem.android.core.networkApi.recruitment.impl.di.RecruitmentRepositoryApiProvider
import odoo.miem.android.core.networkApi.userInfo.impl.di.UserInfoRepositoryApiProvider
import odoo.miem.android.core.networkApi.userModules.impl.di.UserModulesRepositoryApiProvider
import odoo.miem.android.core.platform.dependecies.DefaultPlatformDependencies
import odoo.miem.android.core.platform.dependecies.PlatformDependencies
import odoo.miem.android.core.platform.di.PlatformApiProvider
import odoo.miem.android.core.utils.di.RxApiProvider
import odoo.miem.android.feature.authorization.base.impl.di.AuthorizationScreenApiProvider
import odoo.miem.android.feature.crm.impl.di.CrmScreenApiProvider
import odoo.miem.android.feature.employees.impl.di.EmployeesScreenApiProvider
import odoo.miem.android.feature.moduleNotFound.impl.di.ModuleNotFoundScreenApiProvider
import odoo.miem.android.feature.recruitment.impl.di.RecruitmentScreenApiProvider
import odoo.miem.android.feature.selectingModules.impl.di.SelectingModulesScreenApiProvider
import odoo.miem.android.feature.userProfile.impl.di.UserProfileScreenApiProvider

/**
 * [OdooAppComponent] - **Dagger** component, which is the parent component
 * for all  other components and provides [ApiResolver] with a common **Map** with all
 * **Api**
 *
 * Dependencies:
 *  - [PlatformDependencies] - request platform objects to general *DI graph*
 *
 * Connected modules that deliver specific dependencies:
 * Repositories:
 *  - [AuthorizationRepositoryApiProvider] - authorization repository
 *  - [UserInfoRepositoryApiProvider] - user info repository
 *  - [UserModulesRepositoryApiProvider] - user modules repository
 *  - [RecruitmentRepositoryApiProvider] - recruitment repository
 *  - [EmployeesRepositoryApiProvider] - employees repository
 *  - [FirebaseRemoteConfigApiProvider] - Firebase Remote Config,
 *  - [FirebaseDatabaseApiProvider] - Firebase Firestore and Storage
 *  - [DataStoreApiProvider] - DataStore
 *
 * Screens:
 *  - [AuthorizationScreenApiProvider] - authorization screen
 *  - [SelectingModulesScreenApiProvider] - selecting modules screen
 *  - [ModuleNotFoundScreenApiProvider] - module not found screen
 *  - [RecruitmentScreenApiProvider] - recruitment screen
 *  - [CrmScreenApiProvider] - crm screen
 *  - [UserProfileScreenApiProvider] - user profile screen
 *  - [EmployeesScreenApiProvider] - employees screen
 *
 * Interactors:
 *  - [AuthorizationInteractorApiProvider] - authorization interactor
 *  - [SelectingModulesInteractorApiProvider] - selecting modules interactor
 *  - [RecruitmentInteractorApiProvider] - recruitment interactor
 *  - [EmployeesInteractorApiProvider] - employees interactor
 *
 * Utils:
 *  - [MoshiSerializerApiProvider] - Moshi serializer
 *  - [PlatformApiProvider] - platform objects
 *  - [RxApiProvider] - RX chains utils
 *
 * @author Vorozhtsov Mikhail
 */
@Component(
    dependencies = [
        PlatformDependencies::class,
    ],
    modules = [
        // Repositories
        AuthorizationRepositoryApiProvider::class,
        CrmRepositoryApiProvider::class,
        UserInfoRepositoryApiProvider::class,
        UserModulesRepositoryApiProvider::class,
        EmployeesRepositoryApiProvider::class,
        RecruitmentRepositoryApiProvider::class,
        FirebaseRemoteConfigApiProvider::class,
        FirebaseDatabaseApiProvider::class,
        DataStoreApiProvider::class,

        // Screens
        AuthorizationScreenApiProvider::class,
        SelectingModulesScreenApiProvider::class,
        ModuleNotFoundScreenApiProvider::class,
        RecruitmentScreenApiProvider::class,
        CrmScreenApiProvider::class,
        UserProfileScreenApiProvider::class,
        EmployeesScreenApiProvider::class,

        // Interactors
        AuthorizationInteractorApiProvider::class,
        SelectingModulesInteractorApiProvider::class,
        RecruitmentInteractorApiProvider::class,
        EmployeesInteractorApiProvider::class,

        // Utils
        MoshiSerializerApiProvider::class,
        PlatformApiProvider::class,
        RxApiProvider::class,
    ]
)
interface OdooAppComponent {

    val apiResolver: ApiResolver
}

/**
 * Initialization of application's **DI graph**
 *
 * @param context is needed for initialization of [PlatformDependencies]
 */
fun Context.initApis(
    context: Context,
): OdooAppComponent = DaggerOdooAppComponent
    .builder()
    .platformDependencies(DefaultPlatformDependencies(context))
    .build()
    .also {
        ApiRegistry.init(it.apiResolver)
    }
