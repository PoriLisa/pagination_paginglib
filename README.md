# pagination_paginglib
/### for  3.0.0-alpha06  lib
the new version of paging lib is alpha 3.0.0-alpha06 
for the pagination we have to add below  lib in to our dependency


dependencies {
  def paging_version = "3.0.0-alpha06"

  implementation "androidx.paging:paging-runtime:$paging_version"

  // alternatively - without Android dependencies for tests
  testImplementation "androidx.paging:paging-common:$paging_version"

  // optional - RxJava2 support
  implementation "androidx.paging:paging-rxjava2:$paging_version"

  // optional - Guava ListenableFuture support
  implementation "androidx.paging:paging-guava:$paging_version"
}

.here we have to change our datasource to PagingSource  , without changing also we can do this.

