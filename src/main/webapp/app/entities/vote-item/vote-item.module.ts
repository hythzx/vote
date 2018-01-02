import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { VoteSharedModule } from '../../shared';
import {
    VoteItemService,
    VoteItemPopupService,
    VoteItemComponent,
    VoteItemDetailComponent,
    VoteItemDialogComponent,
    VoteItemPopupComponent,
    VoteItemDeletePopupComponent,
    VoteItemDeleteDialogComponent,
    voteItemRoute,
    voteItemPopupRoute,
    VoteItemResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...voteItemRoute,
    ...voteItemPopupRoute,
];

@NgModule({
    imports: [
        VoteSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        VoteItemComponent,
        VoteItemDetailComponent,
        VoteItemDialogComponent,
        VoteItemDeleteDialogComponent,
        VoteItemPopupComponent,
        VoteItemDeletePopupComponent,
    ],
    entryComponents: [
        VoteItemComponent,
        VoteItemDialogComponent,
        VoteItemPopupComponent,
        VoteItemDeleteDialogComponent,
        VoteItemDeletePopupComponent,
    ],
    providers: [
        VoteItemService,
        VoteItemPopupService,
        VoteItemResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class VoteVoteItemModule {}
