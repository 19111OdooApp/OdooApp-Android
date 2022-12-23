package odoo.miem.android.core.utils.rx.schedule

object Watson {

    @Volatile
    private var reporter: Reporter? = null

    fun setReporter(reporter: Reporter?) {
        this.reporter = reporter
    }

    fun analyzeAndReportBug(throwable: Throwable?) {
        if (throwable == null) {
            return
        }
        reporter?.analyzeAndReport(throwable)
    }

    interface Reporter {
        fun analyzeAndReport(throwable: Throwable)
    }
}