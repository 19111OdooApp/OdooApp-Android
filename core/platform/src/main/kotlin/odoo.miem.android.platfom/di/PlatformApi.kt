package odoo.miem.android.platfom.di

import android.content.Context
import odoo.miem.android.di.Api

/**
 * [PlatformApi] предназначен для обертки платформенных объектов для последующего
 * предоставления интерфейса в компоненты **Dagger 2**
 *
 * @property context является экземпляром абстрактного класса [Context]
 *
 * @see Api
 *
 * @author Ворожцов Михаил
 */
interface PlatformApi : Api {

    val context: Context
}
