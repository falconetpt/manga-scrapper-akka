package com.rfgomes.manga4all

import com.rfgomes.manga4all.manga.domain.{MangaChapter, MangaChapterImages, MangaChapterList, MangaInfo, SearchManga}
import com.rfgomes.manga4all.scrapper.client.MockClient
import com.rfgomes.manga4all.scrapper.scrapper.ManganeloScrapper
import org.scalatest.wordspec.AnyWordSpecLike

import scala.io.Source


class ManganeloScrapperSpec extends AnyWordSpecLike {
  val readFile = (filePath: String) => Source.fromFile(filePath).mkString
  val manganelo = scrapper.Source.manganelo

  "latest mangas" should {

    "return a list of mangas" in {
      val scrapper = ManganeloScrapper(MockClient(readFile("src/test/resources/manganelo/latest.html")))
      val tryLatest = scrapper.getLatest(1)
      val expectedResult = List(
        MangaInfo("rz922457", "Seishun Re:Try" , "https://avt.mkklcdnv6.com/44/s/20-1584234477.jpg", manganelo),
        MangaInfo("kcwy275231567048798", "Here U Are", "https://avt.mkklcdnv6.com/20/l/16-1583494205.jpg", manganelo),
        MangaInfo("last_paradise", "Last Paradise", "https://avt.mkklcdnv6.com/23/o/16-1583494313.jpg", manganelo)
      )

      assert(tryLatest.isSuccess, "Response is sucessful")
      assert(tryLatest.get == expectedResult, "List is the same as expected")
    }

  }

  "popular mangas" should {

    "return a list of mangas" in {
      val scrapper = ManganeloScrapper(MockClient(readFile("src/test/resources/manganelo/popular.html")))
      val tryLatest = scrapper.getPopular(1)
      val expectedResult = List(
        MangaInfo("hyer5231574354229", "Tales of Demons and Gods", "https://avt.mkklcdnv6.com/19/v/1-1583464475.jpg", manganelo),
        MangaInfo("pn918005", "Solo Leveling", "https://avt.mkklcdnv6.com/30/a/17-1583496340.jpg", manganelo),
        MangaInfo("dtdc220351567737255", "Star Martial God Technique", "https://avt.mkklcdnv6.com/32/i/14-1583490877.jpg", manganelo)
      )

      assert(tryLatest.isSuccess, "Response is sucessful")
      assert(tryLatest.get == expectedResult, "List is the same as expected")
    }

  }


  "search mangas" should {

    "return a list of mangas" in {
      val scrapper = ManganeloScrapper(MockClient(readFile("src/test/resources/manganelo/search.html")))
      val tryLatest = scrapper.search(SearchManga("one piece", 1))
      val expectedResult = List(
        MangaInfo("read_one_piece_manga_online_free4", "One Piece", "https://avt.mkklcdnv6.com/3/u/1-1583463814.jpg", manganelo),
        MangaInfo("kimi_no_kakera", "Kimi no Kakera", "https://avt.mkklcdnv6.com/42/a/12-1583487419.jpg", manganelo),
        MangaInfo("doujin_sakka_collection_himegoto", "Doujin Sakka Collection - himegoto", "https://avt.mkklcdnv6.com/40/l/13-1583489252.jpg", manganelo)
      )

      assert(tryLatest.isSuccess, "Response is sucessful")
      assert(tryLatest.get == expectedResult, "List is the same as expected")
    }

  }

  "manga chapter" should {

    "return a list of chapters" in {
      val scrapper = ManganeloScrapper(MockClient(readFile("src/test/resources/manganelo/chapter.html")))
      val tryLatest = scrapper.extractChapterList(MangaInfo("read_one_piece_manga_online_free4", "One Piece", "https://avt.mkklcdnv6.com/3/u/1-1583463814.jpg", manganelo))
      val expectedResult = MangaChapterList(
        id = "read_one_piece_manga_online_free4",
        name = "One Piece",
        description = "",
        imgUrl = "https://avt.mkklcdnv6.com/3/u/1-1583463814.jpg",
        chapters = List(
          MangaChapter("read_one_piece_manga_online_free4", "chapter_1.2", "One Piece chapter Chapter 1.2 : Romance Dawn [Version 2]", manganelo),
          MangaChapter("read_one_piece_manga_online_free4", "chapter_1.1", "One Piece chapter Chapter 1.1 : Romance Dawn [Version 1]", manganelo),
          MangaChapter("read_one_piece_manga_online_free4", "chapter_1", "One Piece chapter Chapter 1 : Romance Dawn", manganelo)
        )
      )

      assert(tryLatest.isSuccess, "Response is sucessful")
      assert(tryLatest.get == expectedResult, "List is the same as expected")
    }

  }

  "manga images" should {

    "return the manga images with prev and next filled" in {
      val scrapper = ManganeloScrapper(MockClient(readFile("src/test/resources/manganelo/imagesWithNextAndPrev.html")))
      val tryLatest = scrapper.extractChapterImages(MangaChapter("pn918005", "chapter_109", "chapter 109", manganelo))
      val expectedResult = MangaChapterImages(
        "pn918005",
        "chapter_109",
        (1 to 46).map(i => s"https://s3.mkklcdnv3.com/mangakakalot/p1/pn918005/chapter_109/$i.jpg").toList,
        Some("chapter_108"),
        Some("chapter_110"),
        manganelo
      )

      assert(tryLatest.isSuccess, "Response is sucessful")
      assert(tryLatest.get == expectedResult, "List is the same as expected")
    }

    "return the manga images with only prev filled" in {
      val scrapper = ManganeloScrapper(MockClient(readFile("src/test/resources/manganelo/imagesWithPrev.html")))
      val tryLatest = scrapper.extractChapterImages(MangaChapter("pn918005", "chapter_110", "chapter 109", manganelo))
      val expectedResult = MangaChapterImages(
        "pn918005",
        "chapter_110",
        (1 to 57).map(i => s"https://s3.mkklcdnv3.com/mangakakalot/p1/pn918005/chapter_110_season_1_finale/$i.jpg").toList,
        Some("chapter_109"),
        None,
        manganelo
      )

      assert(tryLatest.isSuccess, "Response is sucessful")
      assert(tryLatest.get == expectedResult, "List is the same as expected")
    }

    "return the manga images with only next filled" in {
      val scrapper = ManganeloScrapper(MockClient(readFile("src/test/resources/manganelo/imagesWithNext.html")))
      val tryLatest = scrapper.extractChapterImages(MangaChapter("pn918005", "chapter_0", "chapter_0", manganelo))
      val expectedResult = MangaChapterImages(
        "pn918005",
        "chapter_0",
        (1 to 9).map(i => s"https://s3.mkklcdnv3.com/mangakakalot/p1/pn918005/chapter_0_prologue/$i.jpg").toList,
        None,
        Some("chapter_1"),
        manganelo
      )

      assert(tryLatest.isSuccess, "Response is sucessful")
      assert(tryLatest.get == expectedResult, "List is the same as expected")
    }

  }


}
