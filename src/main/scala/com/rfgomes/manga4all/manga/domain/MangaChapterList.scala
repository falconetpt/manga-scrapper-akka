package com.rfgomes.manga4all.manga.domain

case class MangaChapterList(id: String,
                            name: String,
                            description: String,
                            imgUrl: String,
                            chapters: List[MangaChapter])
