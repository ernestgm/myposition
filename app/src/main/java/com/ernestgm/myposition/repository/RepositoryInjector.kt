package com.ernestgm.myposition.repository

import com.ernestgm.myposition.repository.local.IRepositoryLocal
import com.ernestgm.myposition.repository.local.LocalDataManager

internal object RepositoryInjector {

    fun getRepositoryLocal(): IRepositoryLocal {
        return LocalDataManager
    }
}