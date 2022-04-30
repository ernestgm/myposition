package com.ernestgm.myposition.repository

import com.ernestgm.myposition.repository.local.IRepositoryLocal

object Repository {

    val local: IRepositoryLocal
        get() = RepositoryInjector.getRepositoryLocal()

}