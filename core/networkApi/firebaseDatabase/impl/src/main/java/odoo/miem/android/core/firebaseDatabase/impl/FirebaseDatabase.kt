package odoo.miem.android.core.firebaseDatabase.impl

import android.net.Uri
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import io.reactivex.rxjava3.core.Single
import odoo.miem.android.core.networkApi.firebaseDatabase.api.IFirebaseDatabase
import odoo.miem.android.core.networkApi.firebaseDatabase.api.source.FavouriteModulesResponse
import odoo.miem.android.core.networkApi.firebaseDatabase.api.source.ModuleIconResponse
import odoo.miem.android.core.networkApi.firebaseDatabase.api.source.StatusIconResponse
import odoo.miem.android.core.networkApi.firebaseDatabase.api.source.UserRequest
import timber.log.Timber
import javax.inject.Inject

/**
 * [FirebaseDatabase] - wrapper of Firebase Firestore and Storage for fetching icons
 *
 * @author Egor Danilov
 */
class FirebaseDatabase @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val storage: FirebaseStorage
) : IFirebaseDatabase {

    override fun fetchModuleIcons(): Single<List<ModuleIconResponse>> {
        Timber.d("fetchModuleIcons()")

        return Single.create { emitter ->
            firestore.collection(FIRESTORE_MODULE_COLLECTION)
                .get()
                .continueWithTask { query ->
                    val iconTasks = mutableListOf<Task<ModuleIconResponse>>()

                    query.result.map { module ->
                        val moduleName = module.data[MODULE_NAME_FIELD] as? String
                        val iconUrl = module.data[MODULE_ICON_URL_FIELD] as? String

                        iconUrl?.let {
                            val task = fetchFileFromStorage(it)
                                .continueWith { task ->
                                    ModuleIconResponse(
                                        moduleName = moduleName,
                                        downloadUrl = task.result.toString()
                                    )
                                }
                                .addOnFailureListener { e ->
                                    Timber.e("fetchFileFromStorage(): error = ${e.message}")
                                }

                            iconTasks.add(task)
                        }
                    }

                    Tasks.whenAllSuccess<ModuleIconResponse>(iconTasks)
                }
                .addOnSuccessListener { result ->
                    Timber.d("fetchModulesIcons(): icons = $result")
                    emitter.onSuccess(result)
                }
                .addOnFailureListener { e ->
                    Timber.e("fetchModuleIcons(): error = ${e.message}")
                    emitter.onError(e)
                }
        }
    }

    override fun fetchStatusIcons(): Single<List<StatusIconResponse>> {
        Timber.d("fetchStatusIcons()")

        return Single.create { emitter ->
            firestore.collection(FIRESTORE_STATUS_COLLECTION)
                .get()
                .continueWithTask { query ->
                    val iconTasks = mutableListOf<Task<StatusIconResponse>>()

                    query.result.map { module ->
                        val moduleName = module.data[STATUS_NAME_FIELD] as? String
                        val iconUrl = module.data[STATUS_ICON_URL_FIELD] as? String

                        iconUrl?.let {
                            val task = fetchFileFromStorage(it)
                                .continueWith { task ->
                                    StatusIconResponse(
                                        statusName = moduleName,
                                        downloadUrl = task.result.toString()
                                    )
                                }
                                .addOnFailureListener { e ->
                                    Timber.e("fetchFileFromStorage(): error = ${e.message}")
                                }

                            iconTasks.add(task)
                        }
                    }

                    Tasks.whenAllSuccess<StatusIconResponse>(iconTasks)
                }
                .addOnSuccessListener { result ->
                    Timber.d("fetchStatusIcons(): icons = $result")
                    emitter.onSuccess(result)
                }
                .addOnFailureListener { e ->
                    Timber.e("fetchStatusIcons(): error = ${e.message}")
                    emitter.onError(e)
                }
        }
    }

    private fun fetchFileFromStorage(fileReference: String): Task<Uri> {
        return storage.getReferenceFromUrl(fileReference).downloadUrl
    }

    override fun fetchFavouriteModules(
        uid: Int,
        userName: String
    ): Single<FavouriteModulesResponse> {
        Timber.d("fetchFavouriteModules()")

        return Single.create { emitter ->
            firestore.collection(FIRESTORE_USER_COLLECTION)
                .whereEqualTo(USER_UID_FIELD, uid)
                .whereEqualTo(USER_NAME_FIELD, userName)
                .get()
                .continueWith { query ->

                    val favouriteModules = (query.result
                        .documents
                        .firstOrNull()
                        ?.data
                        ?.get(USER_FAVOURITE_MODULES_FIELD) as? List<*>)
                        ?.map { it.toString() }
                        ?: emptyList()

                    FavouriteModulesResponse(
                        modules = favouriteModules
                    )
                }
                .addOnSuccessListener { result ->
                    Timber.d("fetchFavouriteModules(): result = $result")
                    emitter.onSuccess(result)
                }
                .addOnFailureListener { e ->
                    Timber.e("fetchFavouriteModules(): error = ${e.message}")
                    emitter.onError(e)
                }
        }
    }

    override fun addOrUpdateUser(
        uid: Int,
        userName: String,
        favouriteModules: List<String>
    ): Single<Boolean> {
        Timber.d("addOrUpdateUser()")

        return Single.create { emitter ->
            firestore.collection(FIRESTORE_USER_COLLECTION)
                .whereEqualTo(USER_UID_FIELD, uid)
                .whereEqualTo(USER_NAME_FIELD, userName)
                .get()
                .continueWithTask { query ->
                    val documents = query.result.documents

                    val task = if (documents.isEmpty()) {
                        val newUser = UserRequest(
                            uid = uid,
                            name = userName,
                            favouriteModules = favouriteModules
                        )

                        firestore.collection(FIRESTORE_USER_COLLECTION).add(newUser)
                    } else {
                        val documentId = documents.first().id

                        firestore.collection(FIRESTORE_USER_COLLECTION)
                            .document(documentId)
                            .update(USER_FAVOURITE_MODULES_FIELD, favouriteModules)
                    }

                    task
                }
                .addOnSuccessListener {
                    Timber.d(
                        "addOrUpdateUser(): successfully added or updated user " +
                            "with uid = $uid, name = $userName"
                    )
                    emitter.onSuccess(true)
                }
                .addOnFailureListener { e ->
                    Timber.e("addOrUpdateUser(): error = ${e.message}")
                    emitter.onError(e)
                }
        }
    }

    private companion object {
        const val FIRESTORE_MODULE_COLLECTION = "moduleIcons"

        const val MODULE_NAME_FIELD = "moduleName"
        const val MODULE_ICON_URL_FIELD = "iconUrl"

        const val FIRESTORE_STATUS_COLLECTION = "statusIcons"

        const val STATUS_NAME_FIELD = "statusName"
        const val STATUS_ICON_URL_FIELD = "iconUrl"

        const val FIRESTORE_USER_COLLECTION = "users"

        const val USER_UID_FIELD = "uid"
        const val USER_NAME_FIELD = "name"
        const val USER_FAVOURITE_MODULES_FIELD = "favouriteModules"
    }
}
