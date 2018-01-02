/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { VoteTestModule } from '../../../test.module';
import { VoteItemDialogComponent } from '../../../../../../main/webapp/app/entities/vote-item/vote-item-dialog.component';
import { VoteItemService } from '../../../../../../main/webapp/app/entities/vote-item/vote-item.service';
import { VoteItem } from '../../../../../../main/webapp/app/entities/vote-item/vote-item.model';
import { VoteService } from '../../../../../../main/webapp/app/entities/vote';

describe('Component Tests', () => {

    describe('VoteItem Management Dialog Component', () => {
        let comp: VoteItemDialogComponent;
        let fixture: ComponentFixture<VoteItemDialogComponent>;
        let service: VoteItemService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [VoteTestModule],
                declarations: [VoteItemDialogComponent],
                providers: [
                    VoteService,
                    VoteItemService
                ]
            })
            .overrideTemplate(VoteItemDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(VoteItemDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(VoteItemService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new VoteItem(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.voteItem = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'voteItemListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new VoteItem();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.voteItem = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'voteItemListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
