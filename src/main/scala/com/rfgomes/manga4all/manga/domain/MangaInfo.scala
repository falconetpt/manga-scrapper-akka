package com.rfgomes.manga4all.manga.domain

import com.rfgomes.manga4all.scrapper.Source

case class MangaInfo(id: String, name: String, imageUrl: String, source: Source)
