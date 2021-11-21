import java.io.{BufferedReader, InputStreamReader}

object Boot extends App {
  val p = Runtime.getRuntime.exec("./blackbox.macosx")
  val input = new BufferedReader(new InputStreamReader(p.getInputStream))
  println(input.readLine)
  input.close()
}
