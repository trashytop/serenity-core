package net.thucydides.core.reports.adaptors

import net.thucydides.core.model.TestOutcome


class MyAdaptor implements TestOutcomeAdaptor {
    @Override
    List<TestOutcome> loadOutcomes() throws IOException {
        return null  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    List<TestOutcome> loadOutcomesFrom(File source) throws IOException {
        return null
    }
}
