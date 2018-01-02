import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { VoteItem } from './vote-item.model';
import { VoteItemPopupService } from './vote-item-popup.service';
import { VoteItemService } from './vote-item.service';
import { Vote, VoteService } from '../vote';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-vote-item-dialog',
    templateUrl: './vote-item-dialog.component.html'
})
export class VoteItemDialogComponent implements OnInit {

    voteItem: VoteItem;
    isSaving: boolean;

    votes: Vote[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private voteItemService: VoteItemService,
        private voteService: VoteService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.voteService.query()
            .subscribe((res: ResponseWrapper) => { this.votes = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.voteItem.id !== undefined) {
            this.subscribeToSaveResponse(
                this.voteItemService.update(this.voteItem));
        } else {
            this.subscribeToSaveResponse(
                this.voteItemService.create(this.voteItem));
        }
    }

    private subscribeToSaveResponse(result: Observable<VoteItem>) {
        result.subscribe((res: VoteItem) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: VoteItem) {
        this.eventManager.broadcast({ name: 'voteItemListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackVoteById(index: number, item: Vote) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-vote-item-popup',
    template: ''
})
export class VoteItemPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private voteItemPopupService: VoteItemPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.voteItemPopupService
                    .open(VoteItemDialogComponent as Component, params['id']);
            } else {
                this.voteItemPopupService
                    .open(VoteItemDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
