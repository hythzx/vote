/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { Headers } from '@angular/http';

import { VoteTestModule } from '../../../test.module';
import { VoteItemComponent } from '../../../../../../main/webapp/app/entities/vote-item/vote-item.component';
import { VoteItemService } from '../../../../../../main/webapp/app/entities/vote-item/vote-item.service';
import { VoteItem } from '../../../../../../main/webapp/app/entities/vote-item/vote-item.model';

describe('Component Tests', () => {

    describe('VoteItem Management Component', () => {
        let comp: VoteItemComponent;
        let fixture: ComponentFixture<VoteItemComponent>;
        let service: VoteItemService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [VoteTestModule],
                declarations: [VoteItemComponent],
                providers: [
                    VoteItemService
                ]
            })
            .overrideTemplate(VoteItemComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(VoteItemComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(VoteItemService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new VoteItem(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.voteItems[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
