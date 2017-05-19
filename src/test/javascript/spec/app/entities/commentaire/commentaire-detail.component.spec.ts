import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { KekenTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { CommentaireDetailComponent } from '../../../../../../main/webapp/app/entities/commentaire/commentaire-detail.component';
import { CommentaireService } from '../../../../../../main/webapp/app/entities/commentaire/commentaire.service';
import { Commentaire } from '../../../../../../main/webapp/app/entities/commentaire/commentaire.model';

describe('Component Tests', () => {

    describe('Commentaire Management Detail Component', () => {
        let comp: CommentaireDetailComponent;
        let fixture: ComponentFixture<CommentaireDetailComponent>;
        let service: CommentaireService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [KekenTestModule],
                declarations: [CommentaireDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    CommentaireService,
                    EventManager
                ]
            }).overrideComponent(CommentaireDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CommentaireDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CommentaireService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Commentaire(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.commentaire).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
