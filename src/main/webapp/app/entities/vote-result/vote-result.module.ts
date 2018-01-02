import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { VoteSharedModule } from '../../shared';
import {
    VoteResultService,
    VoteResultPopupService,
    VoteResultComponent,
    VoteResultDetailComponent,
    VoteResultDialogComponent,
    VoteResultPopupComponent,
    VoteResultDeletePopupComponent,
    VoteResultDeleteDialogComponent,
    voteResultRoute,
    voteResultPopupRoute,
    VoteResultResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...voteResultRoute,
    ...voteResultPopupRoute,
];

@NgModule({
    imports: [
        VoteSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        VoteResultComponent,
        VoteResultDetailComponent,
        VoteResultDialogComponent,
        VoteResultDeleteDialogComponent,
        VoteResultPopupComponent,
        VoteResultDeletePopupComponent,
    ],
    entryComponents: [
        VoteResultComponent,
        VoteResultDialogComponent,
        VoteResultPopupComponent,
        VoteResultDeleteDialogComponent,
        VoteResultDeletePopupComponent,
    ],
    providers: [
        VoteResultService,
        VoteResultPopupService,
        VoteResultResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class VoteVoteResultModule {}
