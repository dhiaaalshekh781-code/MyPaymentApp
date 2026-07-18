import io.github.jan_tennert.supabase.createSupabaseClient
import io.github.jan_tennert.supabase.postgrest.Postgrest

val supabase = createSupabaseClient(
    supabaseUrl = "https://bexlpwqzmwaloafnrrle.supabase.co",
    supabaseKey = "sb_publishable_GiIkF-_TW4vmTRnYbeQ49w_8_fx0PjL"
) {
    install(Postgrest)
}
