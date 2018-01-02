/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';

import { VoteTestModule } from '../../../test.module';
import { VoteItemDetailComponent } from '../../../../../../main/webapp/app/entities/vote-item/vote-item-detail.component';
import { VoteItemService } from '../../../../../../main/webapp/app/entities/vote-item/vote-item.service';
import { VoteItem } from '../../../../../../main/webapp/app/entities/vote-item/vote-item.model';

describe('Component Tests', () => {

    describe('VoteItem Management Detail Component', () => {
        let comp: VoteItemDetailComponent;
        let fixture: ComponentFixture<VoteItemDetailComponent>;
        let service: VoteItemService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [VoteTestModule],
                declarations: [VoteItemDetailComponent],
                providers: [
                    VoteItemService
                ]
            })
            .overrideTemplate(VoteItemDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(VoteItemDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(VoteItemService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new VoteItem(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.voteItem).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
