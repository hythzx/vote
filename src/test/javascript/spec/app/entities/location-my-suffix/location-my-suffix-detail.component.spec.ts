/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';

import { VoteTestModule } from '../../../test.module';
import { LocationMySuffixDetailComponent } from '../../../../../../main/webapp/app/entities/location-my-suffix/location-my-suffix-detail.component';
import { LocationMySuffixService } from '../../../../../../main/webapp/app/entities/location-my-suffix/location-my-suffix.service';
import { LocationMySuffix } from '../../../../../../main/webapp/app/entities/location-my-suffix/location-my-suffix.model';

describe('Component Tests', () => {

    describe('LocationMySuffix Management Detail Component', () => {
        let comp: LocationMySuffixDetailComponent;
        let fixture: ComponentFixture<LocationMySuffixDetailComponent>;
        let service: LocationMySuffixService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [VoteTestModule],
                declarations: [LocationMySuffixDetailComponent],
                providers: [
                    LocationMySuffixService
                ]
            })
            .overrideTemplate(LocationMySuffixDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(LocationMySuffixDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LocationMySuffixService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new LocationMySuffix(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.location).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
