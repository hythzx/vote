import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { VoteRegionMySuffixModule } from './region-my-suffix/region-my-suffix.module';
import { VoteCountryMySuffixModule } from './country-my-suffix/country-my-suffix.module';
import { VoteLocationMySuffixModule } from './location-my-suffix/location-my-suffix.module';
import { VoteDepartmentMySuffixModule } from './department-my-suffix/department-my-suffix.module';
import { VoteTaskMySuffixModule } from './task-my-suffix/task-my-suffix.module';
import { VoteEmployeeMySuffixModule } from './employee-my-suffix/employee-my-suffix.module';
import { VoteJobMySuffixModule } from './job-my-suffix/job-my-suffix.module';
import { VoteJobHistoryMySuffixModule } from './job-history-my-suffix/job-history-my-suffix.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        VoteRegionMySuffixModule,
        VoteCountryMySuffixModule,
        VoteLocationMySuffixModule,
        VoteDepartmentMySuffixModule,
        VoteTaskMySuffixModule,
        VoteEmployeeMySuffixModule,
        VoteJobMySuffixModule,
        VoteJobHistoryMySuffixModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class VoteEntityModule {}
