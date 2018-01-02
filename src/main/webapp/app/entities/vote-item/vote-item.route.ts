import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { VoteItemComponent } from './vote-item.component';
import { VoteItemDetailComponent } from './vote-item-detail.component';
import { VoteItemPopupComponent } from './vote-item-dialog.component';
import { VoteItemDeletePopupComponent } from './vote-item-delete-dialog.component';

@Injectable()
export class VoteItemResolvePagingParams implements Resolve<any> {

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

export const voteItemRoute: Routes = [
    {
        path: 'vote-item',
        component: VoteItemComponent,
        resolve: {
            'pagingParams': VoteItemResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'voteApp.voteItem.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'vote-item/:id',
        component: VoteItemDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'voteApp.voteItem.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const voteItemPopupRoute: Routes = [
    {
        path: 'vote-item-new',
        component: VoteItemPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'voteApp.voteItem.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'vote-item/:id/edit',
        component: VoteItemPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'voteApp.voteItem.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'vote-item/:id/delete',
        component: VoteItemDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'voteApp.voteItem.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
