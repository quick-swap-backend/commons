package quickswap.commons.domain.shared

import kotlin.collections.joinToString

object LogHelper {
  fun format(
    action: LogActions,
    type: String,
    task: String,
    vararg values: Pair<String, Any>
  ): String {
    val valuesString = values.joinToString(separator = ", ") { (key, value) ->
      "$key=$value"
    }
    return "[${action.name} - $type] $task : $valuesString"
  }

}
enum class LogActions {
  TRY,
  OK,
  FAIL
}