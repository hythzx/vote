import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { VoteItem } from './vote-item.model';
import { VoteItemPopupService } from './vote-item-popup.service';
import { VoteItemService } from './vote-item.service';

@Component({
    selector: 'jhi-vote-item-delete-dialog',
    templateUrl: './vote-item-delete-dialog.component.html'
})
export class VoteItemDeleteDialogComponent {

    voteItem: VoteItem;

    constructor(
        private voteItemService: VoteItemService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.voteItemService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'voteItemListModification',
                content: 'Deleted an voteItem'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-vote-item-delete-popup',
    template: ''
})
export class VoteItemDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private voteItemPopupService: VoteItemPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.voteItemPopupService
                .open(VoteItemDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
