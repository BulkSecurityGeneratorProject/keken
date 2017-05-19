import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { KekenTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { BiereDetailComponent } from '../../../../../../main/webapp/app/entities/biere/biere-detail.component';
import { BiereService } from '../../../../../../main/webapp/app/entities/biere/biere.service';
import { Biere } from '../../../../../../main/webapp/app/entities/biere/biere.model';

describe('Component Tests', () => {

    describe('Biere Management Detail Component', () => {
        let comp: BiereDetailComponent;
        let fixture: ComponentFixture<BiereDetailComponent>;
        let service: BiereService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [KekenTestModule],
                declarations: [BiereDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    BiereService,
                    EventManager
                ]
            }).overrideComponent(BiereDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(BiereDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BiereService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Biere(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.biere).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
