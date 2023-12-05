package aoc

import fs2.io.file.{Files, Path}
import cats.effect.Concurrent

def getLines[F[_]: Files: Concurrent](path: String): F[List[String]] =
  Files[F].readUtf8Lines(Path(path)).compile.toList

def getString[F[_]: Files: Concurrent](path: String): F[String] =
  Files[F].readUtf8(Path(path)).compile.string

def getNonEmptyLines[F[_]: Files: Concurrent](path: String): F[List[String]] =
  Files[F].readUtf8Lines(Path(path)).filter(_.nonEmpty).compile.toList
