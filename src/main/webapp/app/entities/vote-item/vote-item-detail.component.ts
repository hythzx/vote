import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { VoteItem } from './vote-item.model';
import { VoteItemService } from './vote-item.service';

@Component({
    selector: 'jhi-vote-item-detail',
    templateUrl: './vote-item-detail.component.html'
})
export class VoteItemDetailComponent implements OnInit, OnDestroy {

    voteItem: VoteItem;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private voteItemService: VoteItemService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInVoteItems();
    }

    load(id) {
        this.voteItemService.find(id).subscribe((voteItem) => {
            this.voteItem = voteItem;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInVoteItems() {
        this.eventSubscriber = this.eventManager.subscribe(
            'voteItemListModification',
            (response) => this.load(this.voteItem.id)
        );
    }
}
