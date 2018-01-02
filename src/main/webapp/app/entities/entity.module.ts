import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { VoteVoteModule } from './vote/vote.module';
import { VoteVoteItemModule } from './vote-item/vote-item.module';
import { VoteVoteResultModule } from './vote-result/vote-result.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        VoteVoteModule,
        VoteVoteItemModule,
        VoteVoteResultModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class VoteEntityModule {}
