import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { KekenTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { UserExtraDetailComponent } from '../../../../../../main/webapp/app/entities/user-extra/user-extra-detail.component';
import { UserExtraService } from '../../../../../../main/webapp/app/entities/user-extra/user-extra.service';
import { UserExtra } from '../../../../../../main/webapp/app/entities/user-extra/user-extra.model';

describe('Component Tests', () => {

    describe('UserExtra Management Detail Component', () => {
        let comp: UserExtraDetailComponent;
        let fixture: ComponentFixture<UserExtraDetailComponent>;
        let service: UserExtraService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [KekenTestModule],
                declarations: [UserExtraDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    UserExtraService,
                    EventManager
                ]
            }).overrideComponent(UserExtraDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(UserExtraDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(UserExtraService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new UserExtra(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.userExtra).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
