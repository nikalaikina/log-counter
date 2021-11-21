package com.github.nikalaikina.coral

import cats.effect.{IO, Resource}
import fs2.Stream

import java.io.{BufferedReader, InputStream, InputStreamReader}

object ProcessOutputStream {

  def runProcess(command: String): Resource[IO, Stream[IO, String]] = {
    for {
      process <- Resource.eval(IO.delay(Runtime.getRuntime.exec(command)))
      reader <- input(process.getInputStream)
    } yield stream(reader)
  }

  private def input(stream: InputStream): Resource[IO, BufferedReader] = {
    Resource.make(IO.delay(new BufferedReader(new InputStreamReader(stream))))(r =>
      IO.delay(r.close())
    )
  }

  private def stream(reader: BufferedReader): Stream[IO, String] = {
    Stream.repeatEval(IO.delay(reader.readLine()))
  }
}
