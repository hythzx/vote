import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { VoteResultComponent } from './vote-result.component';
import { VoteResultDetailComponent } from './vote-result-detail.component';
import { VoteResultPopupComponent } from './vote-result-dialog.component';
import { VoteResultDeletePopupComponent } from './vote-result-delete-dialog.component';

@Injectable()
export class VoteResultResolvePagingParams implements Resolve<any> {

    constructor(private paginationUtil: JhiPaginationUtil) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const page = route.queryParams['page'] ? route.queryParams['page'] : '1';
        const sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
        return {
            page: this.paginationUtil.parsePage(page),
            predicate: this.paginationUtil.parsePredicate(sort),
            ascending: this.paginationUtil.parseAscending(sort)
      };
    }
}

export const voteResultRoute: Routes = [
    {
        path: 'vote-result',
        component: VoteResultComponent,
        resolve: {
            'pagingParams': VoteResultResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'voteApp.voteResult.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'vote-result/:id',
        component: VoteResultDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'voteApp.voteResult.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const voteResultPopupRoute: Routes = [
    {
        path: 'vote-result-new',
        component: VoteResultPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'voteApp.voteResult.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'vote-result/:id/edit',
        component: VoteResultPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'voteApp.voteResult.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'vote-result/:id/delete',
        component: VoteResultDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'voteApp.voteResult.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
